package charbosses.cards.red;

import downfall.util.TextureLoader;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.monsters.BronzeOrbWhoReallyLikesDefectForSomeReason;
import charbosses.monsters.MushroomPurple;
import charbosses.monsters.MushroomRed;
import charbosses.monsters.MushroomWhite;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import downfall.monsters.NeowBoss;
import expansioncontent.expansionContentMod;

import java.util.ArrayList;
import java.util.Collections;

public class EnSummonMushrooms extends AbstractBossCard {
    public static final String ID = "downfall:SummonMushrooms";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public EnSummonMushrooms() {
        super(ID, cardStrings.NAME, expansionContentMod.makeCardPath("SummonMushrooms.png"), 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        portrait = TextureLoader.getTextureAsAtlasRegion(expansionContentMod.makeCardPath("SummonMushrooms.png"));
        portraitImg = TextureLoader.getTexture(expansionContentMod.makeCardPath("SummonMushrooms.png"));

    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        for (AbstractMonster m2 : AbstractDungeon.getMonsters().monsters) {
            if (!m2.isDead && !m2.isDying && !(m2 instanceof AbstractCharBoss) && !(m2 instanceof NeowBoss)) {
                this.addToBot(new VFXAction(new BiteEffect(m2.hb.cX, m2.hb.cY - 20.0F * Settings.scale, Color.SCARLET.cpy()), 0.3F));
                addToBot(new HealAction(m, m, m2.currentHealth));
                addToBot(new WaitAction(0.1F));
                this.addToBot(new DamageAction(m2, new DamageInfo(m, m.currentHealth, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
                addToBot(new WaitAction(0.1F));
                addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, 1),1));
                addToBot(new WaitAction(0.1F));
            }
        }

        ArrayList<AbstractMonster> mushroomList = new ArrayList<>();
        mushroomList.add(new MushroomPurple(-400F, 0F));
        mushroomList.add(new MushroomRed(-400F, 0F));
        mushroomList.add(new MushroomWhite(-400F, 0F));
        Collections.shuffle(mushroomList);

        ArrayList<AbstractMonster> mushroomList2 = new ArrayList<>();
        mushroomList2.add(new MushroomPurple(-600F, 0F));
        mushroomList2.add(new MushroomRed(-600F, 0F));
        mushroomList2.add(new MushroomWhite(-600F, 0F));
        Collections.shuffle(mushroomList2);

        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(mushroomList.get(0), true));
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(mushroomList2.get(0), true));

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnSummonMushrooms();
    }
}
