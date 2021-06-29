package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import downfall.cards.OctoChoiceCard;
import downfall.util.SelectCardsCenteredAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class Transmogrify extends AbstractSneckoCard {

    public final static String ID = makeID("Transmogrify");

    //stupid intellij stuff SKILL, SELF, RARE

    public Transmogrify() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
        tags.add(CardTags.HEALING);
    }

    public static AbstractRelic returnTrueRandomScreenlessRelic(AbstractRelic.RelicTier tier) {
        ArrayList<AbstractRelic> eligibleRelicsList = new ArrayList<>();
        ArrayList<AbstractRelic> myGoodStuffList = new ArrayList<>();
        switch (tier) {
            case DEPRECATED:
            case STARTER:
            case SPECIAL:
                eligibleRelicsList.add(RelicLibrary.getRelic(Circlet.ID));
                break;
            case COMMON:
                for (String r : AbstractDungeon.commonRelicPool) {
                    eligibleRelicsList.add(RelicLibrary.getRelic(r));
                }
                break;
            case UNCOMMON:
                for (String r : AbstractDungeon.uncommonRelicPool) {
                    eligibleRelicsList.add(RelicLibrary.getRelic(r));
                }
                break;
            case RARE:
                for (String r : AbstractDungeon.rareRelicPool) {
                    eligibleRelicsList.add(RelicLibrary.getRelic(r));
                }
                break;
            case BOSS:
                for (String r : AbstractDungeon.bossRelicPool) {
                    eligibleRelicsList.add(RelicLibrary.getRelic(r));
                }
                break;
            case SHOP:
                for (String r : AbstractDungeon.shopRelicPool) {
                    eligibleRelicsList.add(RelicLibrary.getRelic(r));
                }
                break;
        }

        try {
            for (AbstractRelic r : eligibleRelicsList)
                if (r.getClass().getMethod("onEquip").getDeclaringClass() == AbstractRelic.class && r.getClass().getMethod("onUnequip").getDeclaringClass() == AbstractRelic.class) {
                    myGoodStuffList.add(r);
                }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (myGoodStuffList.isEmpty()) {
            return new Circlet();
        } else {
            myGoodStuffList.removeIf(r -> AbstractDungeon.player.hasRelic(r.relicId));
            return myGoodStuffList.get(AbstractDungeon.cardRandomRng.random(myGoodStuffList.size() - 1));
        }
    }

    private static boolean equipCheck(AbstractRelic r) throws NoSuchMethodException {
        //Returns true if the relic does NOT override equip or unequip effects.
        return r.getClass().getMethod("onEquip").getDeclaringClass() == AbstractRelic.class && r.getClass().getMethod("onUnequip").getDeclaringClass() == AbstractRelic.class;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractRelic> eligibleRelicsList = new ArrayList<>(AbstractDungeon.player.relics);
        eligibleRelicsList.removeIf(c -> c.tier == AbstractRelic.RelicTier.STARTER || c.tier == AbstractRelic.RelicTier.SPECIAL);
        eligibleRelicsList.removeIf(c -> {
            try {
                return !equipCheck(c);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return false;
            }
        });
        if (!eligibleRelicsList.isEmpty()) {
            Collections.shuffle(eligibleRelicsList, AbstractDungeon.cardRandomRng.random);
            AbstractRelic q = eligibleRelicsList.get(0);
            if (eligibleRelicsList.size() == 1) {
                q.flash();
                AbstractDungeon.player.loseRelic(q.relicId);
                att(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, returnTrueRandomScreenlessRelic(q.tier));
                    }
                });
            }
            else {
                AbstractRelic q2 = eligibleRelicsList.get(1);
                ArrayList<AbstractCard> cardChoices = new ArrayList<>();
                cardChoices.add(new OctoChoiceCard(q.relicId, q.name, getCorrectPlaceholderImage(ID), EXTENDED_DESCRIPTION[2] + q.name + EXTENDED_DESCRIPTION[3] + q.tier.name().toLowerCase(Locale.ROOT) + EXTENDED_DESCRIPTION[4], CardColor.COLORLESS));
                cardChoices.add(new OctoChoiceCard(q2.relicId, q2.name, getCorrectPlaceholderImage(ID), "Lose " + q2.name + " and obtain another " + q2.tier.name().toLowerCase(Locale.ROOT) + " relic.", CardColor.COLORLESS));
                atb(new SelectCardsCenteredAction(cardChoices, 1, EXTENDED_DESCRIPTION[1], (cards) -> {
                    AbstractRelic r = AbstractDungeon.player.getRelic(cards.get(0).cardID);
                    r.flash();
                    AbstractDungeon.player.loseRelic(r.relicId);
                    att(new AbstractGameAction() {
                        @Override
                        public void update() {
                            isDone = true;
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, returnTrueRandomScreenlessRelic(r.tier));
                        }
                    });
                }));
            }
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean newMusic = super.canUse(p, m);
        if (p.relics.stream().anyMatch(r -> {
            try {
                return (r.tier != AbstractRelic.RelicTier.STARTER && r.tier != AbstractRelic.RelicTier.SPECIAL && equipCheck(r));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return false;
        })) {
            return newMusic;
        }
        cantUseMessage = EXTENDED_DESCRIPTION[0];
        return false;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}