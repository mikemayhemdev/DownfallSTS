package sneckomod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import slimebound.SlimeboundMod;
import sneckomod.powers.TransmogrifyPower;
import sneckomod.vfx.AnnouncementEffect;

import java.util.ArrayList;

public class Transmogrify extends AbstractSneckoCard {

    public final static String ID = makeID("Transmogrify");

    //stupid intellij stuff SKILL, SELF, RARE

    public Transmogrify() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
        tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractRelic> eligibleRelicsList = new ArrayList<>(AbstractDungeon.player.relics);
        eligibleRelicsList.removeIf(c -> c.tier == AbstractRelic.RelicTier.STARTER || c.tier == AbstractRelic.RelicTier.SPECIAL);
        if (!eligibleRelicsList.isEmpty()) {
            AbstractRelic q = eligibleRelicsList.get(AbstractDungeon.cardRandomRng.random(eligibleRelicsList.size() - 1));
            q.flash();
            AbstractDungeon.player.loseRelic(q.relicId);
            //AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, s);
            //AbstractDungeon.effectsQueue.add(new AnnouncementEffect(Color.PURPLE.cpy(), q.name + " transformed to " + s.name + "!", 5.5F));
            applyToSelf(new TransmogrifyPower(q));
        }
    }


    public static AbstractRelic returnTrueRandomScreenlessRelic(AbstractRelic.RelicTier tier) {
        ArrayList<AbstractRelic> eligibleRelicsList = new ArrayList<>();
        ArrayList<AbstractRelic> myGoodStuffList = new ArrayList<>();
        if (tier == AbstractRelic.RelicTier.COMMON) {
            for (String r : AbstractDungeon.commonRelicPool) {
                eligibleRelicsList.add(RelicLibrary.getRelic(r));
            }
        } else if (tier == AbstractRelic.RelicTier.UNCOMMON) {
            for (String r : AbstractDungeon.uncommonRelicPool) {
                eligibleRelicsList.add(RelicLibrary.getRelic(r));
            }
        } else if (tier == AbstractRelic.RelicTier.RARE) {
            for (String r : AbstractDungeon.rareRelicPool) {
                eligibleRelicsList.add(RelicLibrary.getRelic(r));
            }
        } else if (tier == AbstractRelic.RelicTier.SHOP) {
            for (String r : AbstractDungeon.shopRelicPool) {
                eligibleRelicsList.add(RelicLibrary.getRelic(r));
            }
        } else if (tier == AbstractRelic.RelicTier.STARTER) {
            eligibleRelicsList.addAll(RelicLibrary.starterList);
        }
        SlimeboundMod.logger.info(tier);
        SlimeboundMod.logger.info(eligibleRelicsList);

        myGoodStuffList.removeIf(r -> AbstractDungeon.player.hasRelic(r.relicId));

        if (myGoodStuffList.isEmpty()) {
            return new Circlet();
        } else {
            return myGoodStuffList.get(AbstractDungeon.cardRandomRng.random(myGoodStuffList.size() - 1));
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}