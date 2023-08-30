package charbosses.cards.curses;

import basemod.helpers.CardModifierManager;
import charbosses.cards.AbstractCustomBossCard;
import downfall.util.TextureLoader;
import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import expansioncontent.cardmods.PropertiesMod;

public class EnHaunted extends AbstractCustomBossCard {
    public static final String ID = "downfall_Charboss:Haunted";
    private static final CardStrings cardStrings;
    public static final String IMG_PATH = downfallMod.assetPath("images/cards/haunted.png");

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("downfall:Haunted");
    }

    public EnHaunted() {
        super(ID, EnHaunted.cardStrings.NAME, IMG_PATH, -2, EnHaunted.cardStrings.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE, AbstractMonster.Intent.NONE);
        this.isEthereal = true;
        portrait = TextureLoader.getTextureAsAtlasRegion(IMG_PATH);
        portraitImg = TextureLoader.getTexture(IMG_PATH);
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractCard c : AbstractCharBoss.boss.hand.group) {
                    if (!c.isEthereal) {
                        CardModifierManager.addModifier(c, new PropertiesMod(PropertiesMod.supportedProperties.ETHEREAL, false));
                        c.superFlash(Color.PURPLE.cpy());
                    }
                }
            }
        });
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnHaunted();
    }
}
