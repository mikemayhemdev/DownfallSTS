package downfall.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BlueCandle;
import downfall.actions.WaitForEscapeAction;
import downfall.downfallMod;
import downfall.patches.EvilModeCharacterSelect;
import hermit.characters.hermit;
import slimebound.characters.SlimeboundCharacter;

import static downfall.patches.EvilModeCharacterSelect.evilMode;
import static hermit.characters.hermit.Enums.HERMIT;


/// This relic exists to quickgrab its description, images, and texts to replace blue candle while playing evil mode, like hecktoplasm.
/// Do not remove it. Also, it doesn't work, the real patch uses an override. But still, don't touch this.
public class BlackCandle extends CustomRelic {

    public static final String ID = downfallMod.makeID("BlackCandle");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/BlackCandle.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/BlackCandleOutline.png"));

    public BlackCandle() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.CURSE) {
            this.flash();
            if (card.cost == -2) {
                this.flash();
                this.addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 1, AbstractGameAction.AttackEffect.FIRE));
                card.exhaust = true;
                action.exhaustCard = true;
            }
        }
    }

    public boolean canSpawn() {
        return (evilMode || (AbstractDungeon.player instanceof hermit));
    }

    public AbstractRelic makeCopy() {
        return new BlackCandle();
    }
}
