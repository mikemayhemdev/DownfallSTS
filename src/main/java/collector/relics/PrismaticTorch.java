package collector.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import collector.CollectorMod;
import collector.actions.GainReservesAction;
import collector.cards.Ember;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.util.TextureLoader;

import static collector.util.Wiz.atb;
import static collector.util.Wiz.makeInHand;

public class PrismaticTorch extends CustomRelic {
    public static final String ID = CollectorMod.makeID(PrismaticTorch.class.getSimpleName());
    private static final String IMG_PATH = PrismaticTorch.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = PrismaticTorch.class.getSimpleName() + ".png";

    private static final int EMBER_COUNT = 2;

    public PrismaticTorch() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.BOSS, LandingSound.MAGICAL);
        this.tips.add(new CardPowerTip(new Ember()));
    }

    @Override
    public void atBattleStart() {
        flash();
        makeInHand(new Ember(), EMBER_COUNT);
    //    atb(new GainReservesAction(1));
    }

    @Override
    public void atTurnStart() {
        grayscale=false;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (!grayscale) {
            if (card.cardID.equals(Ember.ID)) {
                flash();
                atb(new GainReservesAction(1));
                grayscale=true;
            }
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(EmeraldTorch.ID);
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(EmeraldTorch.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(EmeraldTorch.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }


    @Override
    public String getUpdatedDescription() {
        // Colorize the starter relic's name
        String name = new EmeraldTorch().name;
        StringBuilder sb = new StringBuilder();
        if (Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT) {
            sb.append("[#").append(CollectorMod.characterColor.toString()).append("]").append(name).append("[]");

        } else {
            for (String word : name.split(" ")) {
                sb.append("[#").append(CollectorMod.characterColor.toString()).append("]").append(word).append("[] ");
            }
            sb.setLength(sb.length() - 1);
            sb.append("[#").append(CollectorMod.characterColor.toString()).append("]");
        }

        return DESCRIPTIONS[0] + sb + DESCRIPTIONS[1];
    }
}
