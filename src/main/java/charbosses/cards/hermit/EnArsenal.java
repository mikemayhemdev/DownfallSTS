package charbosses.cards.hermit;

import basemod.ReflectionHacks;
import charbosses.bosses.Hermit.Simpler.ArchetypeAct3BasicsSimple;
import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.SnakeDagger;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import downfall.util.TextureLoader;
import expansioncontent.expansionContentMod;
import hermit.characters.hermit;

public class EnArsenal extends AbstractBossCard {
    public static final String ID = "downfall:Arsenal";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public EnArsenal() {
        super(ID, cardStrings.NAME, expansionContentMod.makeCardPath("SummonMushrooms.png"), 1, cardStrings.DESCRIPTION, CardType.SKILL, hermit.Enums.COLOR_YELLOW, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        portrait = TextureLoader.getTextureAsAtlasRegion(expansionContentMod.makeCardPath("SummonMushrooms.png"));
        portraitImg = TextureLoader.getTexture(expansionContentMod.makeCardPath("SummonMushrooms.png"));

    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        for (AbstractMonster m2 : AbstractDungeon.getMonsters().monsters) {
            if (!m2.isDead && !m2.isDying && m2 instanceof SnakeDagger) {
                addToBot(new VFXAction(m2, new InflameEffect(m), 0.2F));
                addToBot(new SuicideAction(m2));
                addToBot(new HideHealthBarAction(m2));
            }
        }

        addToBot(new SpawnMonsterAction(ArchetypeAct3BasicsSimple.getDoomedSnake(), true));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnArsenal();
    }

}
