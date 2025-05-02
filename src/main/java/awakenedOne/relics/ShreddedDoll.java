package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.Ceremony;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.relics.ChampionCrown;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gremlin.cards.Ward;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;

public class ShreddedDoll extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("ShreddedDoll");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("ShreddedDoll.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("ShreddedDoll.png"));

    public ShreddedDoll() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractCard c = new Ceremony();
        c.upgrade();
        this.addToTop(new MakeTempCardInHandAction(c, 1, false));
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RitualPower(AbstractDungeon.player, 1, true)));
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(RippedDoll.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(RippedDoll.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(RippedDoll.ID);
    }

    @Override
    public String getUpdatedDescription() {
        // Colorize the starter relic's name
        String name = new RippedDoll().name;
        StringBuilder sb = new StringBuilder();
        if(Settings.language== Settings.GameLanguage.ZHS|| Settings.language== Settings.GameLanguage.ZHT){
            sb.append("[#").append(AwakenedOneMod.placeholderColor.toString()).append("]").append(name).append("[]");

        }else {
            for (String word : name.split(" ")) {
                sb.append("[#").append(AwakenedOneMod.placeholderColor.toString()).append("]").append(word).append("[] ");
            }
            sb.setLength(sb.length() - 1);
            sb.append("[#").append(AwakenedOneMod.placeholderColor.toString()).append("]");
        }

        return DESCRIPTIONS[0] + sb + DESCRIPTIONS[1];
    }

}
