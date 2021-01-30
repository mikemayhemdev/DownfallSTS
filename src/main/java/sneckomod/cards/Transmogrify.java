package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import sneckomod.powers.TransmogrifyPower;

import java.util.ArrayList;

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
            AbstractRelic q = eligibleRelicsList.get(AbstractDungeon.cardRandomRng.random(eligibleRelicsList.size() - 1));
            q.flash();
            AbstractDungeon.player.loseRelic(q.relicId);
            //AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, s);
            //AbstractDungeon.effectsQueue.add(new AnnouncementEffect(Color.PURPLE.cpy(), q.name + " transformed to " + s.name + "!", 5.5F));
            applyToSelf(new TransmogrifyPower(q));
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