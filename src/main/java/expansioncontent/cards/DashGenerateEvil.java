package expansioncontent.cards;


import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import expansioncontent.powers.EvilWithinPower;
import com.megacrit.cardcrawl.powers.PanachePower;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;
import expansioncontent.expansionContentMod;
import expansioncontent.actions.RandomCardWithTagAction;

import static expansioncontent.expansionContentMod.loadJokeCardImage;

import java.util.ArrayList;

public class DashGenerateEvil extends AbstractExpansionCard {
    public final static String ID = makeID("DashGenerateEvil");

    private static final int MAGIC = 10;
    private static final int downfallMagic = 5;

    public DashGenerateEvil() {
        super(ID, 0, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_power.png", "expansioncontentResources/images/1024/bg_boss_power.png");
        baseDownfallMagic = downfallMagic;
        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;
        loadJokeCardImage(this, "DashGenerateEvil.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.player.hasPower(EvilWithinPower.POWER_ID)) {
                this.addToBot(new ApplyPowerAction(p, p, new EvilWithinPower(p, this.magicNumber), this.magicNumber));
        }
        this.addToBot(new ApplyPowerAction(p, p, new EvilWithinPower(p, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(4);
    }}}
